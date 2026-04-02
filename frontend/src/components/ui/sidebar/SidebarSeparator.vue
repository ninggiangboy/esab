<script setup lang="ts">
import { cn } from '@/lib/utils'

import UiSeparator from '../UiSeparator.vue'
</script>

<template>
  <UiSeparator
    data-slot="sidebar-separator"
    data-sidebar="separator"
    orientation="horizontal"
    :class="cn('bg-border mx-2 w-auto', $attrs.class as string | undefined)"
  />
</template>
