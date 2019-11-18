export interface Criteria {
    isMandatory: boolean;
    name: string;
    type: string;
    unit?: string;
    value: string;
}

export interface UniqueCriteria {
    isMandatory: boolean;
    name: string;
    type: string;
    unit?: string;
    values: string[];
}
