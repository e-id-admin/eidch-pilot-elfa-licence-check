import {Attribute} from '@app/_models/attribute';

export class AttributeGroup {
  name: string;
  order?: number = null;
  attributes: Attribute[];

  constructor(name: string, order: number, attributes: Attribute[]) {
    this.name = name;
    this.order = order;
    this.attributes = attributes;
  }

  getAttribute(name: string) {
    return this.attributes.find(attribute => attribute.name === name);
  }
}
