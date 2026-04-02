<script setup lang="ts">
import UiInput from '@/components/ui/UiInput.vue'
import UiLabel from '@/components/ui/UiLabel.vue'
</script>

<template>
  <div class="grid max-w-xs gap-1.5">
    <UiLabel for="nf-qty">Quantity</UiLabel>
    <UiInput id="nf-qty" type="number" min="1" placeholder="1" />
  </div>
</template>
