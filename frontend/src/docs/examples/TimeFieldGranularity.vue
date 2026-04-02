<script setup lang="ts">
import UiInput from '@/components/ui/UiInput.vue'
import UiLabel from '@/components/ui/UiLabel.vue'
</script>

<template>
  <div class="grid max-w-xs gap-3">
    <div class="grid gap-1.5">
      <UiLabel>Hours / minutes</UiLabel>
      <UiInput type="time" step="60" />
    </div>
    <div class="grid gap-1.5">
      <UiLabel>With seconds</UiLabel>
      <UiInput type="time" step="1" />
    </div>
  </div>
</template>
