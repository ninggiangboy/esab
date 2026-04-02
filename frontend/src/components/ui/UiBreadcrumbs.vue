<script setup lang="ts">
import { cn } from '@/lib/utils'
</script>

<template>
  <nav
    aria-label="Breadcrumb"
    :class="
      cn(
        'flex flex-wrap items-center gap-1.5 break-words text-sm text-muted-foreground sm:gap-2.5',
        $attrs.class as string | undefined,
      )
    "
  >
    <ol class="m-0 flex list-none flex-wrap items-center gap-1.5 p-0 sm:gap-2.5">
      <slot />
    </ol>
  </nav>
</template>
