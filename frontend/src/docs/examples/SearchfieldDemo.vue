<script setup lang="ts">
import { ref } from 'vue'

import UiSearchField from '@/components/ui/UiSearchField.vue'

const q = ref('')
</script>

<template>
  <UiSearchField v-model="q" placeholder="Search…" class="w-full" />
</template>
