<template>

  <div>Hey</div>
  <div class="sign-up">
    <!--    <form method="post">-->
    <!--      <ul>-->
    <!--        <li v-for="(us, key) in usersList" :key="key">{{us.userName}}</li>-->
    <!--      </ul>-->
    <!--    </form>-->

    <form @submit.prevent="createUser">
      <div class="form-control">
        <label>userName</label>
        <input type="text" v-model.trim="addUser.userName" name="name" placeholder="Enter Your Username"/>
      </div>
      <div class="form-control">
        <label>Password</label>
        <input type="password" v-model.trim="addUser.password" name="password" placeholder="Enter Your Password"/>
      </div>
      <div class="form-control">
        <label>Confirm-Password</label>
        <input type="password" v-model.trim="confirmPassword" name="confirm-password"
               placeholder="Confirm your password"/>
      </div>


      <div class="form-control">
        <label>firstName</label>
        <input
            type="text"
            v-model.trim="addUser.firstName"
            name="firstName"
            placeholder="Enter First Name"
        />
      </div>
      <div class="form-control">
        <label>LastName</label>
        <input
            type="text"
            v-model.trim="addUser.lastName"
            name="LastName"
            placeholder="Enter Last Name"
        />
      </div>
      <div class="form-control">
        <label>Email</label>
        <input
            type="text"
            v-model.trim="addUser.email"
            name="LastName"
            placeholder="Enter Email"
        />
      </div>

      <input type="submit" value="Save Information"/>
    </form>

    <p>{{ errorMessage }}</p>
  </div>
</template>


<script lang="ts">
import axios from "axios";
import {UserBuilder} from "@/UserRequests/UserBuilder";
import {IUSERINFO} from "@/ModelInterface/IUserInfo";
import {createUserResponse, userRequest} from "@/UserRequests/RequestCatalogue";
import {defineComponent} from "vue";
import {APIENDPOINT} from "@/RequestEndpoints";
import {UserInfo} from "@/Models/UserInfo";


export default defineComponent({

  data() {
    return {
      usersList: new Array<UserInfo>(),
      addUser: {} as userRequest,

      resetFields: {} as userRequest,
      confirmPassword: "",
      errorMessage: ""
    }
  },
  methods: {
    async getUsers() {
      console.log("Connecting to API: ");
      try {
        const {data, status} = await axios
            .get<createUserResponse>(APIENDPOINT, {
                  headers: {
                    Accept: 'application/json',
                  },
                },
            );


        Object.values(data).forEach(el => {
          const user = new UserInfo(el.userId, el.firstName, el.lastName,
              el.email, el.userName);
          this.usersList.push(user);
        })

      } catch (error) {

        if (axios.isAxiosError(error)) {
          return error.message;
        } else {
          return 'An unexpected error occurred';
        }
      }
    },

    createUser() {

      if (this.addUser.password === this.confirmPassword) {

        let userReq: userRequest = {
          firstName: this.addUser.firstName,
          userName: this.addUser.userName,
          lastName: this.addUser.lastName,
          password: this.addUser.password,
          email: this.addUser.email,
          address: undefined,
          userDetails: undefined,

        }
        this.postUser(userReq);
      } else {
        this.errorMessage = "Password does not match"
      }

    },

    postUser(postRequest: userRequest) {
      this.errorMessage = "";

      const headers = {
        "content-type": "Application/json"
      }
      axios.post(APIENDPOINT, postRequest, {headers})
          .then(response => console.log(response))
          .catch(error => {
            this.errorMessage = error.message;
            console.error("There was an error!", error);
          });
      this.resetForm();
    },
    resetForm() {

      this.addUser = {...this.resetFields};
      this.confirmPassword ="";

    },

  },
  buildCreateUserRequest(user: IUSERINFO) {

    const buildUser = new UserBuilder(user.email, user.userName);

    if (user.address) {
      buildUser.address(user.address).build();
      return;
    }
    if (user.details) {
      buildUser.details(user.details).build();
      return;
    }
  },
  mounted: async function () {

    // await this.getUsers();
  }

});


</script>

<style lang="scss">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;

  a {
    font-weight: bold;
    color: #2c3e50;

    &.router-link-exact-active {
      color: #42b983;
    }
  }
}
</style>
