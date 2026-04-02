<script setup lang="ts">
import { ref } from 'vue'

import UiInput from '@/components/ui/UiInput.vue'
import UiLabel from '@/components/ui/UiLabel.vue'

const raw = ref('12.5')
</script>

<template>
  <div class="grid max-w-xs gap-1.5">
    <UiLabel for="nf-cur">Amount (USD)</UiLabel>
    <div class="flex items-center gap-2">
      <span class="text-sm text-muted-foreground">$</span>
      <UiInput
        id="nf-cur"
        type="number"
        min="0"
        step="0.01"
        :value="raw"
        class="min-w-0 flex-1"
        @input="raw = ($event.target as HTMLInputElement).value"
      />
    </div>
  </div>
</template>
