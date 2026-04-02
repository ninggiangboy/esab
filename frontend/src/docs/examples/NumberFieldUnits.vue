<script setup lang="ts">
import UiInput from '@/components/ui/UiInput.vue'
import UiLabel from '@/components/ui/UiLabel.vue'
</script>

<template>
  <div class="grid max-w-xs gap-1.5">
    <UiLabel for="nf-km">Distance</UiLabel>
    <div class="flex items-center gap-2">
      <UiInput id="nf-km" type="number" min="0" step="1" placeholder="42" class="min-w-0 flex-1" />
      <span class="text-sm text-muted-foreground">km</span>
    </div>
  </div>
</template>
