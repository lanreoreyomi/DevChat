<template>
  <div class="home">
    <div v-for="el in users" :key="el.id">
      <p>{{ el }} </p>
    </div>
  </div>

</template>

<script>


export default {
  name: 'App',
  components: {},

  data() {
    return {
      users: []
    }
  },
  mounted() {
     this.getUsers();
  },
  methods: {
    getUsers() {
      fetch('/api/v1/user')
      .then((response) => response.json())
      .then((data) => {
        console.log(data)
        this.users = data
      })
    }
  }
}
</script>


<style>
p {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin: 60px auto;
  background-color: #ecf0f1;
  padding: 20px;
  width: 50%;
  font-weight: bold;

}
</style>
