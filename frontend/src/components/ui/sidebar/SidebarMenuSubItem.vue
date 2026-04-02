<script setup lang="ts">
import { cn } from '@/lib/utils'
</script>

<template>
  <li
    data-slot="sidebar-menu-sub-item"
    data-sidebar="menu-sub-item"
    :class="cn('group/menu-sub-item relative', $attrs.class as string | undefined)"
  >
    <slot />
  </li>
</template>
