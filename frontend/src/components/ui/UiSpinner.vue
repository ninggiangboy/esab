<script setup lang="ts">
import { Loader2 } from 'lucide-vue-next'

import { cn } from '@/lib/utils'
</script>

<template>
  <Loader2
    role="status"
    aria-label="Loading"
    :class="cn('animate-spin', $attrs.class as string | undefined)"
  />
</template>
