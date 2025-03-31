<template>
    <div>
      <h1>Tour Details</h1>
      <p v-if="tour">Von: {{ tour.von }} → {{ tour.nach }}</p>
      <p v-if="tour">Nach: {{ tour.nach || 'Keine zusätzliche Info verfügbar' }}</p>
      <p v-if="tour">Preis: {{ tour.preis }} €</p>
      <p v-if="!tour">Tour nicht gefunden.</p>
    </div>
  </template>
  
  <script setup lang="ts">
  import { computed } from 'vue'
  import { useRoute } from 'vue-router'
  import { useTourenStore } from '@/stores/tourenstore'
  import { useInfo,  } from './../composables/useInfo.ts'
  
  const {info, loescheInfo, setzeInfo} = useInfo()
  const route = useRoute()
  console.log(route)
  const tourenStore = useTourenStore()
  tourenStore.updateTourListe() 
  
  const tourid = computed(() => Number(route.params.tourid)) //route Params enthält die übergebenen Parameter(${tourid})
  const tour = computed(() => tourenStore.tourdata.tourliste.find(t => t.id === tourid.value))
  
if(tour.value && tour.value.entfernung > 300){ //ich muss erst mit tour.value prüfen, ob tour ein Objekt doer undefined zurückgibt, bevor ich auf entfernung zugreife
    setzeInfo("Die Entfernung beträgt über 300 km.")
}

  </script>
  