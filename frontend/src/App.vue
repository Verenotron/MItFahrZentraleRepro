<script setup lang="ts">
import { useInfo } from './composables/useInfo.ts'
import { RouterView } from 'vue-router'
const {info, loescheInfo, setzeInfo} = useInfo() //useInfo wird einmal aufgerufen und benötigte Teile werden extrahiert mit Destructing.
import { onMounted } from 'vue'

onMounted(() => loescheInfo())
console.log("Komponente neu geladen.")

</script>

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
    </footer>
</template>

<style scoped>
@import './assets/style.css';
</style>

