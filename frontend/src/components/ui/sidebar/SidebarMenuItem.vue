<script setup lang="ts">
import { cn } from '@/lib/utils'
</script>

<template>
  <li
    data-slot="sidebar-menu-item"
    data-sidebar="menu-item"
    :class="cn('group/menu-item relative', $attrs.class as string | undefined)"
  >
    <slot />
  </li>
</template>
