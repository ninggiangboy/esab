<script setup lang="ts">
import UiDatePicker from '@/components/ui/UiDatePicker.vue'
import UiLabel from '@/components/ui/UiLabel.vue'
</script>

<template>
  <div class="grid max-w-md gap-1.5">
    <UiLabel for="dp-1">Appointment</UiLabel>
    <UiDatePicker id="dp-1" />
  </div>
</template>
