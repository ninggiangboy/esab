<script setup lang="ts">
import UiDateInput from '@/components/ui/UiDateInput.vue'
import UiLabel from '@/components/ui/UiLabel.vue'
</script>

<template>
  <div class="grid max-w-xs gap-1.5">
    <UiLabel for="df-birth">Birth date</UiLabel>
    <UiDateInput id="df-birth" />
  </div>
</template>
