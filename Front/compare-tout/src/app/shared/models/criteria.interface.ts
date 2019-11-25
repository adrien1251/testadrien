export interface Criteria {
    isMandatory: boolean;
    name: string;
    type: string;
    unit?: string;
    value: string;
}

export interface UniqueCriteria {
    isMandatory?: boolean;
    id?: string;
    name: string;
    type: string;
    unit?: string;
    values: CriteriaValue[];
}

export interface CriteriaValue {
    value: string;
    selected: boolean;
}
