<script setup lang="ts">
import { ref } from 'vue'

import UiCheckbox from '@/components/ui/UiCheckbox.vue'

const selected = ref(new Set<string>(['draft']))

function toggle(id: string, checked: boolean | 'indeterminate') {
  const next = new Set(selected.value)
  if (checked === true) next.add(id)
  else next.delete(id)
  selected.value = next
}
</script>

<template>
  <div class="flex flex-col gap-2">
    <UiCheckbox
      v-for="opt in [
        { id: 'draft', label: 'Draft' },
        { id: 'published', label: 'Published' },
      ]"
      :key="opt.id"
      :checked="selected.has(opt.id)"
      @update:checked="(v) => toggle(opt.id, v)"
    >
      {{ opt.label }}
    </UiCheckbox>
  </div>
</template>
