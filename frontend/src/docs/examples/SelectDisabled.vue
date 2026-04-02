<script setup lang="ts">
import { ref } from 'vue'

import UiSelect from '@/components/ui/UiSelect.vue'

const value = ref('a')
</script>

<template>
  <UiSelect v-model="value" disabled :options="[{ value: 'a', label: 'Disabled select' }]" />
</template>
