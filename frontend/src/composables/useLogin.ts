import { reactive } from "vue"
import { useInfo } from './useInfo.ts'

const { setzeInfo, loescheInfo } = useInfo();

export const loginState = reactive({
    username : '',
    loggedIn : false,
    jwt : ''
})

export function useLogin(){
    return { loginState, login, logout };
}

function logout(){
    loginState.username = '';
    loginState.loggedIn = false;
    loginState.jwt = '';
}

async function login(username : string, passwort : string){

    const response = await fetch('http://localhost:8080/api/token', {
        method: 'POST',
        headers: {'Content-Type' : 'application/json'},
        redirect: 'error',
        body : JSON.stringify({username, passwort})
    })

    if(!response.ok){
        logout()
        setzeInfo("Benutzer wurde nicht gefunden")
        throw new Error("Login fehlgeschlagen")
    }

    const data = await response.text();
    loginState.username = username;
    loginState.loggedIn = true;
    loginState.jwt = data;

}
