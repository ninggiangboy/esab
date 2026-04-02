<script setup lang="ts">
// Multiple selection: native `<select multiple>` — Radix Vue Select is single-value only.
import { ref } from 'vue'

import UiNativeSelect from '@/components/ui/UiNativeSelect.vue'

const selected = ref<string[]>(['apple'])

function onChange(ev: Event) {
  const el = ev.target as HTMLSelectElement
  selected.value = [...el.selectedOptions].map((o) => o.value)
}
</script>

<template>
  <div class="space-y-2">
    <UiNativeSelect multiple class="min-h-[100px]" :value="selected" @change="onChange">
      <option value="apple">Apple</option>
      <option value="banana">Banana</option>
      <option value="orange">Orange</option>
    </UiNativeSelect>
    <p class="m-0 text-xs text-muted-foreground">Hold Cmd/Ctrl to select multiple.</p>
  </div>
</template>
