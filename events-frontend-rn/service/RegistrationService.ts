import { AppUser } from "../assets/types/registration";
import { apiBase } from "../constants/ApiConfig"
export async function isRegistered(accessToken: string) {
    const response = await fetch(`${apiBase}/user/validate`, {
        method: "GET",
        headers: {
        "Content-type": "application/json", 
        authorization: "Bearer " + accessToken},
    });
    console.log(JSON.stringify(response))
    return response.status === 200 ? true : false
}

export async function registerNewUser(user: AppUser, accessToken: string) {
    const newUser = {
      username: user?.username,
      email: user?.email,
      email_verified: user?.emailVerified,
      first_name: user?.firstName,
      last_name: user?.lastName,
      date_of_birth: user?.dateOfBirth,
      primary_phone: user?.primaryPhone,
      gender_code: user?.genderCode,
      activated: true,
      picture: user?.picture,
      user_address: user?.address
    }
    const response = await fetch(`${apiBase}/user/register`, {
      method: "POST", 
      headers: {
        "Content-type": "application/json", 
        authorization: "Bearer " + accessToken},
        body: JSON.stringify(newUser)
      })
    console.log(JSON.stringify(response));
    if(response.status === 200) {
        return true
    } else {
        return false
    } 
  }