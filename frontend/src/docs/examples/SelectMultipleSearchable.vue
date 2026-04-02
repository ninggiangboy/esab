<script setup lang="ts">
import { computed, ref } from 'vue'

import UiInput from '@/components/ui/UiInput.vue'
import UiLabel from '@/components/ui/UiLabel.vue'
import UiNativeSelect from '@/components/ui/UiNativeSelect.vue'

const q = ref('')
const options = ['Vue', 'React', 'Svelte', 'Solid', 'Angular']
const filtered = computed(() =>
  options.filter((o) => o.toLowerCase().includes(q.value.toLowerCase())),
)
</script>

<template>
  <div class="grid w-full gap-2">
    <UiLabel>Filter + multi-select</UiLabel>
    <UiInput
      :value="q"
      placeholder="Search…"
      @input="q = ($event.target as HTMLInputElement).value"
    />
    <UiNativeSelect multiple class="min-h-[100px]">
      <option v-for="o in filtered" :key="o" :value="o">{{ o }}</option>
    </UiNativeSelect>
  </div>
</template>
