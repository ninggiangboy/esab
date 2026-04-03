<script setup lang="ts">
import { Separator } from '@/ui/components/separator'
</script>

<template>
  <div class="flex h-8 w-full max-w-xs items-center gap-2">
    <span class="text-sm">Left</span>
    <Separator orientation="vertical" class="h-full" />
    <span class="text-sm">Right</span>
  </div>
</template>
