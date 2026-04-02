<script setup lang="ts">
import { MoonStar, Sun } from 'lucide-vue-next'
import { computed } from 'vue'

import { useTheme } from '@/composables/useTheme'

import UiButton from './ui/UiButton.vue'

const { theme, setTheme } = useTheme()

const isDark = computed(() => theme.value === 'dark')
</script>

<template>
  <UiButton variant="outline" size="icon" @click="setTheme(isDark ? 'light' : 'dark')">
    <Sun v-if="isDark" class="size-[14px]" />
    <MoonStar v-else class="size-[14px]" />
  </UiButton>
</template>
