<template>

    <h1>Login</h1>

    <form @submit.prevent="loginfunction">

        <label for="Benutzername">Benutzername</label>
        <input name="Benutzername" type="text" v-model="name">

        <label for="Passwort">Passwort</label>
        <input name="Passwort" type="text" v-model="passwort">

        <button type="submit">OK</button>
    </form>

</template>

<script setup lang="ts">
import { useLogin } from '@/composables/useLogin'
import { useRouter } from 'vue-router'
import { ref } from 'vue'

const { loginState, login, logout } = useLogin()
const router = useRouter();

logout()

const name = ref<string>('')
const passwort = ref<string>('')

const loginfunction = async () => {
    await login(name.value, passwort.value)
    if (loginState.loggedIn === true){
        router.push('/')
    }
}

</script>