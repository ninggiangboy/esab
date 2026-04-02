<script setup lang="ts">
import { ref } from 'vue'

import UiSelect from '@/components/ui/UiSelect.vue'

const value = ref('')

const languages = [
  { value: '1', label: 'English' },
  { value: '2', label: 'Spanish' },
  { value: '3', label: 'French' },
  { value: '4', label: 'German' },
  { value: '5', label: 'Italian' },
]
</script>

<template>
  <UiSelect v-model="value" :options="languages" placeholder="Select language" />
</template>
