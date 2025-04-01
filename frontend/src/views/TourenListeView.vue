<template>

  <div>
    <input type="text" placeholder="Such nach Ziel- oder Startort" v-model="suchtext"> <!-- v-model bindet den Wert einer Eingabe(input, textarea) an eine Variable-->
    <button v-on:click="resetSuchtext()">reset</button>
  </div>

  <TourenListe :touren="FilteredTourenListe"></TourenListe> <!-- :touren definiert ein Property, das :touren ist eine Kurzschreibweise für v-bind:touren -->

</template>

<script setup lang="ts"> //Scipt setup ist eine spezielle syntax in vue3, die den Composition API-Code vereinfacht. 
// Es entfernt die default export Strukturund macht Variablen, Props und Funktionen automatisch verfügbar.
import TourenListe from './../components/tour/TourenListe.vue'
import { ref, computed } from 'vue'
import { useTourenStore } from '@/stores/tourenstore.ts'

const tourenStore = useTourenStore() 
tourenStore.updateTourListe() //daten werden erst abgerufen, wenn updateTourListe aufgerufen wird.
const suchtext = ref<string>('') //Generics können auch weggelassen werden

function resetSuchtext(){
  suchtext.value = ''
}

const FilteredTourenListe = computed(() => { // Computed wird verwendet, um reaktive Berechnungen durchzuführen. Wenn eine oder mehrer Variablen mit denen gerechnet wird, reaktiv sind.
  if(suchtext.value === ''){ //Wert wird neu berechnet, wenn Wert der Reaktiven Variablen sich ändert.
    return tourenStore.tourdata.tourliste
  }

  return tourenStore.tourdata.tourliste.filter(tour => {
    const von = tour.startOrtName.toLowerCase()
    const nach = tour.zielOrtName.toLowerCase()
    return von.includes(suchtext.value.toLowerCase()) || nach.includes(suchtext.value.toLowerCase())
  })
})

</script>

