<template>

    <tr>
        <td>{{tour.abfahrt}}</td>
        <td>{{tour.von}}</td>
        <td>{{tour.nach}}</td>
        <td>{{tour.entfernung}}</td>
        <td>{{tour.plaetze}}</td>
        <td>{{frei}}</td>
        <td>{{tour.preis}}</td>
        <td class="noBorderElement"> 
        <button v-on:click="details">Details</button>
          
        </td>
    </tr>

</template>

<script setup lang="ts"> //Script setup braucht keinen Default-Export, da die Datei automatisch als Komponente registriert wird.
import { defineProps, computed } from 'vue'
import type { ITourDTD } from '@/stores/ITourDTD'
import router from '@/router'

const frei = computed(() => props.tour.plaetze - props.tour.buchungen) //computed erstellt einen reaktiven Wert (Funktion aus der Vue Composition API). 
//Differenz wird neu berechnet, sobald sich plaetze oder buchungen ändern. Verhält sich wie ein reaktiver Listener. 

const props = defineProps<{
  tour: ITourDTD
}>()

console.log("Daten :: " , props.tour)


function details() {
  router.push({ path: `/tour/${props.tour.id}` }) //navigiert zur TourView.vue
}

</script>
<style scoped>
@import './../../assets/style.css';
</style>