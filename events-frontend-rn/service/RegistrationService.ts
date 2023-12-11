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

export async function registerNewUser(user: AppUser, accessToken?: string) {
  if(accessToken) {
      const response = await fetch(`${apiBase}/user/register`, {
        method: "POST", 
        headers: {
          "Content-type": "application/json", 
          authorization: "Bearer " + accessToken},
          body: JSON.stringify(user)
        })
        console.log(JSON.stringify(response));
        if(response.status === 201) {
          return true
        }  
    }
    return false
  }