<script setup lang="ts">
import { ref } from 'vue'

import UiSelect from '@/components/ui/UiSelect.vue'

const value = ref('apple')
</script>

<template>
  <UiSelect
    v-model="value"
    clearable
    placeholder="None"
    :options="[
      { value: 'apple', label: 'Apple' },
      { value: 'banana', label: 'Banana' },
    ]"
  />
</template>
