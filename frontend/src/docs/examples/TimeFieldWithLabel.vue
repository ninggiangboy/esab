<script setup lang="ts">
import UiInput from '@/components/ui/UiInput.vue'
import UiLabel from '@/components/ui/UiLabel.vue'
</script>

<template>
  <div class="grid max-w-xs gap-1.5">
    <UiLabel for="tf-alarm">Alarm</UiLabel>
    <UiInput id="tf-alarm" type="time" />
  </div>
</template>
