<script setup lang="ts">
import UiSelect from '@/components/ui/UiSelect.vue'

const groups = [
  {
    label: 'Citrus',
    options: [
      { value: 'orange', label: 'Orange' },
      { value: 'lemon', label: 'Lemon' },
    ],
  },
  {
    label: 'Berries',
    options: [
      { value: 'strawberry', label: 'Strawberry' },
      { value: 'blueberry', label: 'Blueberry' },
    ],
  },
]
</script>

<template>
  <UiSelect :groups="groups" placeholder="Choose a fruit" />
</template>
