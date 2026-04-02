<script setup lang="ts">
import { computed, ref } from 'vue'

import UiInput from '@/components/ui/UiInput.vue'
import UiLabel from '@/components/ui/UiLabel.vue'
import UiSelect from '@/components/ui/UiSelect.vue'

const q = ref('')
const value = ref('')

const all = ['Apple', 'Banana', 'Orange', 'Grape', 'Kiwi']

const options = computed(() =>
  all
    .filter((o) => o.toLowerCase().includes(q.value.toLowerCase()))
    .map((o) => ({ value: o, label: o })),
)
</script>

<template>
  <div class="grid w-full gap-2">
    <UiLabel>Search + select</UiLabel>
    <UiInput
      :value="q"
      placeholder="Search…"
      @input="q = ($event.target as HTMLInputElement).value"
    />
    <UiSelect v-model="value" :options="options" placeholder="Choose…" />
  </div>
</template>
