<template>
   <header class="kopf">
        <nav class="header-content">
            <div class="div-header">
                <!-- <a class="text" href="@{/benutzer}">Benutzer</a>
                <a class="text" href="@{/ort}">Orte</a> -->
                <RouterLink class="text" to="/touren" @click="loescheInfo()">Touren</RouterLink>
            </div>
            <div class="div-header">
                <a class="text" href="${sprache}'" th:text="#{sprachen} + ' : '"></a>
                <a class="text" href="@{?sprache=de}">DE</a>
                <a class="text" href="@{?sprache=en}">EN</a>
                <a class="text" href="@{?sprache=nl}">NL</a>
                <button @click="userLogout()">Logout</button>  
            </div>      
        </nav>
        
    </header>

    <div id="app">

        <div v-if="info" class="info-box">
        <div class="infobox-header">Obacht! <button @click="loescheInfo()" class="close-btn">x</button></div> 
        <div class="infobox-content">{{ info }}</div>
    </div>
    <button @click="setzeInfo('Dies ist eine Nachricht.')">Hallo</button> <!-- @click ist eine kurzschreibweise für v-on:click-->
        <RouterView />
    </div>
    
    <footer class="footer-content">
        <nav>
            <a class="text" href="#">Imressum</a>
            <a class="text" href="#">Kontakt</a>
        </nav>

        <p>{{ loginState.username }}</p>
    </footer>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { useLogin } from '@/composables/useLogin.ts'
import { useInfo } from './composables/useInfo.ts'
import { RouterView } from 'vue-router'
import { onMounted } from 'vue'

const {info, loescheInfo, setzeInfo} = useInfo() //useInfo wird einmal aufgerufen und benötigte Teile werden extrahiert mit Destructing.
onMounted(() => loescheInfo())
console.log("Komponente neu geladen.")

const { loginState, login, logout } = useLogin();
const router = useRouter();

function userLogout(){
    logout()
    router.push("/login")
}

</script>

<style scoped>
@import './assets/style.css';
</style>

