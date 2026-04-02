<script setup lang="ts">
import UiInput from '@/components/ui/UiInput.vue'
import UiLabel from '@/components/ui/UiLabel.vue'
</script>

<template>
  <div class="grid max-w-xs gap-1.5">
    <UiLabel for="nf-pct">Tax rate</UiLabel>
    <div class="flex items-center gap-2">
      <UiInput
        id="nf-pct"
        type="number"
        min="0"
        max="100"
        step="0.1"
        placeholder="7.25"
        class="min-w-0 flex-1"
      />
      <span class="text-sm text-muted-foreground">%</span>
    </div>
  </div>
</template>
