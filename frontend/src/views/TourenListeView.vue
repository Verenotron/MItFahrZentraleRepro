<template>

  <div>
    <input type="text" placeholder="Such nach Ziel- oder Startort" v-model="suchtext">
    <button v-on:click="resetSuchtext()">reset</button>
  </div>

  <TourenListe :touren="FilteredTourenListe" :suchtext="suchtext.toLowerCase()"></TourenListe>

</template>


<script setup lang="ts"> //Scipt setup ist eine spezielle syntax in vue3, die den Composition API-Code vereinfacht. 
// Es entfernt die default export Strukturund macht Variablen, Props und Funktionen automatisch verfügbar.
import type { ITourDTD } from '@/stores/ITourDTD'
import TourenListe from './../components/tour/TourenListe.vue'
import { ref, computed } from 'vue'
import { useTourenStore } from '@/stores/tourenstore.ts'

const tourenStore = useTourenStore()

tourenStore.updateTourListe() //daten werden erst abgerufen, wenn updateTourListe aufgerufen wird.

const suchtext = ref('')

function resetSuchtext(){
  suchtext.value = ''
}

const FilteredTourenListe = computed(() => {
  if(suchtext.value === ''){
    return tourenStore.tourdata.tourliste
  }

  return tourenStore.tourdata.tourliste.filter(tour => {
    const von = tour.von.toLowerCase()
    const nach = tour.nach.toLowerCase()
    return von.includes(suchtext.value.toLowerCase()) || nach.includes(suchtext.value.toLowerCase())
  })
})

</script>

