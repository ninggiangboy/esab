<script setup lang="ts">
import { docFormToast } from '@/docs/examples/_internal/docFormSubmit'
import { toTypedSchema } from '@vee-validate/zod'
import { z } from 'zod'
import { Button } from '@/ui/components/button'
import { Label } from '@/ui/components/field'
import {
  Form,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
  FormVm,
  useForm,
} from '@/ui/components/form'
import { RadioGroup, RadioGroupItem } from '@/ui/components/radio-group'

const schema = toTypedSchema(z.object({ plan: z.string().min(1) }))
const { handleSubmit } = useForm({ validationSchema: schema, initialValues: { plan: 'pro' } })
const onSubmit = handleSubmit((v) => docFormToast(v))
</script>
<template>
  <Form class="space-y-4" @submit="onSubmit">
    <FormField v-slot="{ componentField }" name="plan" type="radio">
      <FormItem>
        <FormLabel>Plan</FormLabel>
        <FormVm generic="string | undefined" v-slot="vm" :component-field="componentField">
          <RadioGroup v-bind="vm" class="max-w-xs">
            <Label class="flex items-center gap-2 font-normal">
              <RadioGroupItem value="free" />
              Free
            </Label>
            <Label class="flex items-center gap-2 font-normal">
              <RadioGroupItem value="pro" />
              Pro
            </Label>
          </RadioGroup>
        </FormVm>
        <FormMessage />
      </FormItem>
    </FormField>
    <Button type="submit">Submit</Button>
  </Form>
</template>
