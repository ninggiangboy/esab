<script setup lang="ts">
import UiSkeleton from '@/components/ui/UiSkeleton.vue'
</script>

<template>
  <div class="flex items-center space-x-4">
    <UiSkeleton class="size-12 rounded-full" />
    <div class="grow space-y-2">
      <UiSkeleton class="h-4 w-3/4 max-w-[200px]" />
      <UiSkeleton class="h-4 w-1/2 max-w-[160px]" />
    </div>
  </div>
</template>
