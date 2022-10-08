import {IUSERINFO} from "@/ModelInterface/IUserInfo";

export class UserInfo implements IUSERINFO{
    private _email: string;
    private _firstName: string;
    private _lastName: string;
    private _userId: number;
    private _userName: string;
    private _address: undefined;
    private _details: undefined;

    constructor( userId: number, email: string, firstName: string, lastName: string,
               userName: string) {
        this._userId = userId;
        this._email = email;
        this._firstName = firstName;
        this._lastName = lastName;
        this._userName = userName;
    }


    get address(): undefined {
        return this._address;
    }

    set address(value: undefined) {
        this._address = value;
    }

    get details(): undefined {
        return this._details;
    }

    set details(value: undefined) {
        this._details = value;
    }

    get email(): string {
        return this._email;
    }

    set email(value: string) {
        this._email = value;
    }

    get firstName(): string {
        return this._firstName;
    }

    set firstName(value: string) {
        this._firstName = value;
    }

    get lastName(): string {
        return this._lastName;
    }

    set lastName(value: string) {
        this._lastName = value;
    }


    get statusCode(): number {
        return this._userId;
    }

    set statusCode(value: number) {
        this._userId = value;
    }

    get userName(): string {
        return this._userName;
    }

    set userName(value: string) {
        this._userName = value;
    }
}