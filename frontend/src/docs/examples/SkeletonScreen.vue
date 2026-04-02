<script setup lang="ts">
import UiSkeleton from '@/components/ui/UiSkeleton.vue'
</script>

<template>
  <div class="mx-auto mt-4 w-full max-w-[600px] space-y-4">
    <UiSkeleton class="h-8 w-1/3" />
    <UiSkeleton class="h-32 w-full" />
    <div class="grid grid-cols-2 gap-3">
      <UiSkeleton class="h-24 w-full" />
      <UiSkeleton class="h-24 w-full" />
    </div>
  </div>
</template>
