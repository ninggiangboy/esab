<script setup lang="ts">
import { FieldGroup } from '@/ui/components/field'
import { cn } from '@/ui/lib/utils'

defineOptions({ inheritAttrs: false })

defineProps<{
  class?: string
}>()
</script>

<template>
  <FieldGroup :class="cn($props.class)">
    <input
      type="search"
      data-slot="search-input"
      :class="
        cn(
          'flex h-full w-full bg-transparent text-sm placeholder:text-muted-foreground focus-visible:outline-none disabled:cursor-not-allowed disabled:opacity-50',
        )
      "
      v-bind="$attrs"
    />
  </FieldGroup>
</template>
