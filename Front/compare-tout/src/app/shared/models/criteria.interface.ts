export interface Criteria {
    isMandatory: boolean;
    name: string;
    type: string;
    unit: string;
    criteriaUnit?: string;
    value: string;
}

export interface UniqueCriteria {
    isMandatory?: boolean;
    idCriteria?: string;
    id?: string;
    name: string;
    type: string;
    unit?: string;
    defMinValue?: number;
    defMaxValue?: number;
    minValue?: number;
    maxValue?: number;
    values: CriteriaValue[];
}

export interface CriteriaValue {
    value: string;
    selected: boolean;
}
