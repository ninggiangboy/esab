<script setup lang="ts">
import UiSeparator from '@/components/ui/UiSeparator.vue'
</script>

<template>
  <div class="w-full max-w-xs">
    <p class="m-0 text-sm">Section A</p>
    <UiSeparator class="my-3" />
    <p class="m-0 text-sm">Section B</p>
    <UiSeparator class="my-3" orientation="vertical" decorative />
    <p class="m-0 text-sm">Inline split example (vertical separator needs flex row parent).</p>
  </div>
</template>
