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
  
  const route = useRoute()
  console.log(route)
  const tourenStore = useTourenStore()
  tourenStore.updateTourListe() 
  
  const tourid = computed(() => Number(route.params.tourid))
  const tour = computed(() => tourenStore.tourdata.tourliste.find(t => t.id === tourid.value))
  </script>
  