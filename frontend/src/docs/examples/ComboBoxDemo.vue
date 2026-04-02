<script setup lang="ts">
import { ref } from 'vue'

import UiCombobox from '@/components/ui/UiCombobox.vue'

const value = ref('')

const frameworks = ['Vue', 'React', 'Svelte', 'Solid']
</script>

<template>
  <div class="w-full max-w-sm space-y-2">
    <UiCombobox v-model="value" :options="frameworks" placeholder="Pick or type a framework" />
    <p class="m-0 text-xs text-muted-foreground">
      Radix combobox: type to filter, use arrow keys and Enter to choose, or click an option.
    </p>
  </div>
</template>
