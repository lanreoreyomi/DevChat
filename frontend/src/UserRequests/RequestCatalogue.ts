import {IUSERADDRESS, IUSERDETAILS} from "@/ModelInterface/IUserInfo";

export type UserType = {
    userId: number,
    firstName: string,
    userName: string,
    lastName: string,
    password:string,
    email: string,
    address: undefined,
    userDetails: undefined,
    statusCode:number

}

export type createUserResponse={
    data: UserType;
}

export type userRequest = {
    firstName: string,
    userName: string,
    lastName: string,
    password:string,
    email: string,
    address: undefined,
    userDetails: undefined,


}

export type createUserRequest={
    data: userRequest;
}
