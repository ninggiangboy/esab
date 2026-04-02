<script setup lang="ts">
import UiDateRangePicker from '@/components/ui/UiDateRangePicker.vue'
import UiLabel from '@/components/ui/UiLabel.vue'
</script>

<template>
  <div class="grid max-w-md gap-1.5">
    <UiLabel for="trip-dates">Trip dates</UiLabel>
    <UiDateRangePicker id="trip-dates" />
  </div>
</template>
