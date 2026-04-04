<script setup lang="ts">
import { docFormToast } from '@/docs/examples/_internal/docFormSubmit'
import { toTypedSchema } from '@vee-validate/zod'
import { z } from 'zod'
import { Button } from '@/ui/components/button'
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
  useForm,
} from '@/ui/components/form'
import { Input } from '@/ui/components/textfield'

const schema = toTypedSchema(z.object({ username: z.string().min(2) }))
const { handleSubmit } = useForm({ validationSchema: schema })
const onSubmit = handleSubmit((v) => docFormToast(v))
</script>
<template>
  <Form class="w-full space-y-4" @submit="onSubmit">
    <FormField v-slot="{ componentField }" name="username">
      <FormItem>
        <FormLabel>Username</FormLabel>
        <FormControl v-slot="controlProps">
          <Input v-bind="{ ...componentField, ...controlProps }" placeholder="henry" />
        </FormControl>
        <FormMessage />
      </FormItem>
    </FormField>
    <Button type="submit">Submit</Button>
  </Form>
</template>
