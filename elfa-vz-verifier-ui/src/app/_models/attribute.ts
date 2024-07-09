export class Attribute {
  name: string;
  type: string;
  order?: number;

  constructor(name: string, type: string, order: number = null) {
    this.name = name;
    this.type = type;
    this.order = order;
  }
}
