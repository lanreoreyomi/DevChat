import {IUSERADDRESS, IUSERDETAILS, IUSERINFO} from "@/ModelInterface/IUserInfo";


export class UserBuilder {

    private readonly _user: IUSERINFO;

    constructor(email:string, userName: string) {
        this._user = {
            email: email,
            userName: userName,
            firstName: "",
            lastName: "",
            address:undefined,
            details:undefined
        }
    }

    firstName(firstName:string): UserBuilder {
        this._user.firstName = firstName;
        return this;
    }

   lastName(lastName:string): UserBuilder {
        this._user.lastName = lastName;
        return this;
    }
      details(details:IUSERDETAILS): UserBuilder {
        this._user.details = details;
        return this;
    }


    address(address: IUSERADDRESS): UserBuilder {
        this._user.address = address;
        return this;
    }

    build(): IUSERINFO {
        return this._user;
    }
}