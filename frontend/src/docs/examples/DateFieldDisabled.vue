<script setup lang="ts">
import { ref } from 'vue'

import UiDateInput from '@/components/ui/UiDateInput.vue'

const date = ref('2024-06-01')
</script>

<template>
  <UiDateInput v-model="date" disabled />
</template>
