<script setup lang="ts">
import { docFormToast } from '@/docs/examples/_internal/docFormSubmit'
import { toTypedSchema } from '@vee-validate/zod'
import { z } from 'zod'
import { Button } from '@/ui/components/button'
import { DateField } from '@/ui/components/datefield'
import {
  Form,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
  FormVm,
  useForm,
} from '@/ui/components/form'

const schema = toTypedSchema(z.object({ dob: z.any() }))
const { handleSubmit } = useForm({ validationSchema: schema })
const onSubmit = handleSubmit((v) => docFormToast(v))
</script>
<template>
  <Form class="w-full max-w-xs space-y-4" @submit="onSubmit">
    <FormField v-slot="{ componentField }" name="dob">
      <FormItem>
        <FormLabel>Date of birth</FormLabel>
        <FormVm generic="DateValue | undefined" v-slot="vm" :component-field="componentField">
          <DateField v-bind="vm" />
        </FormVm>
        <FormMessage />
      </FormItem>
    </FormField>
    <Button type="submit">Submit</Button>
  </Form>
</template>
