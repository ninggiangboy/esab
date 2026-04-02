<script setup lang="ts">
import { cn } from '@/lib/utils'

import UiInput from '../UiInput.vue'
</script>

<template>
  <UiInput
    data-slot="sidebar-input"
    data-sidebar="input"
    :class="cn('bg-background h-8 w-full shadow-none', $attrs.class as string | undefined)"
  />
</template>
