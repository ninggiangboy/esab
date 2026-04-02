<script setup lang="ts">
import UiLabel from '@/components/ui/UiLabel.vue'
import UiSwitch from '@/components/ui/UiSwitch.vue'
</script>

<template>
  <label
    class="flex cursor-not-allowed items-center justify-between gap-4 text-sm opacity-70 sm:max-w-xs"
  >
    <UiLabel class="font-normal">Disabled</UiLabel>
    <UiSwitch :checked="true" disabled />
  </label>
</template>
